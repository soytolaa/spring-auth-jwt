package com.tola.demoapi.service.serviceImp;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tola.demoapi.model.entities.Otp;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.request.ForgetRequest;
import com.tola.demoapi.model.request.UserLoginRequest;
import com.tola.demoapi.model.request.UserRequest;
import com.tola.demoapi.model.response.TokenResponse;
import com.tola.demoapi.model.response.UserResponse;
import com.tola.demoapi.repository.UserRepository;
import com.tola.demoapi.repository.OtpRepository;
import com.tola.demoapi.service.AuthService;
import com.tola.demoapi.service.EmailService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import com.tola.demoapi.jwt.JwtService;
import com.tola.demoapi.exception.BadRequestException;
import com.tola.demoapi.exception.NotFoundException;
import com.tola.demoapi.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;

    public Integer optCode() { // generate 6 digit random number
        return (int) (Math.random() * 1000000);
    }

    @Override
    @Transactional
    public UserResponse register(UserRequest userRequest) throws MessagingException, UnsupportedEncodingException {
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new BadRequestException("Password and confirm password do not match");
        }
        Optional<User> existUser = userRepository.findByEmail(userRequest.getEmail());
        if (existUser.isPresent()) {
            throw new BadRequestException("Email already exist!");
        }
        User user = User.builder()
                .userName(userRequest.getUserName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .type("credential")
                .isActive(true)
                .isVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Integer otp = optCode();
        emailService.sendEmail(userRequest.getEmail(), otp.toString());
        otpRepository.save(Otp.builder()
                .otpCode(otp)
                .createdAt(LocalDateTime.now())
                .user(user)
                .expiredAt(LocalDateTime.now().plusMinutes(1))
                .isVerified(false)
                .build());
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public UserResponse verifyOtp(Integer otpCode) {
        Optional<Otp> otpEntity = otpRepository.findByOtpCode(otpCode);
        if (otpEntity.isEmpty()) {
            throw new BadRequestException("Invalid OTP!");
        }
        Otp otp = otpEntity.get();
        User user = userRepository.findById(otpEntity.get().getUser().getUserId())
                .orElseThrow(() -> new BadRequestException("User email not found!"));
        if (otp.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP expired!");
        }
        otp.setIsVerified(true);
        user.setIsVerified(true);
        user.setUpdatedAt(LocalDateTime.now());
        otpRepository.save(otp);
        userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public Integer resendOtp(String email) throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException("User email not found!");
        }
        Integer otp = optCode();
        emailService.sendEmail(email, String.valueOf(otp));
        Optional<Otp> otpEntity = otpRepository.findByUser(user.get());
        if (otpEntity.isPresent()) {
            Otp existingOtp = otpEntity.get();
            if (!existingOtp.getIsVerified()) {
                existingOtp.setOtpCode(otp);
                existingOtp.setCreatedAt(LocalDateTime.now());
                existingOtp.setExpiredAt(LocalDateTime.now().plusMinutes(1));
                existingOtp.setIsVerified(false);
                otpRepository.save(existingOtp);
            }
        }
        return otp;
    }

    @Override
    public TokenResponse login(UserLoginRequest userLoginRequest)
            throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userRepository.findByEmail(userLoginRequest.getEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("User email not found!");
        }
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.get().getPassword())) {
            throw new BadRequestException("Invalid password!");
        }
        if (!user.get().getIsVerified()) {
            Integer otp = optCode();
            emailService.sendEmail(user.get().getEmail(), String.valueOf(otp));
            Optional<Otp> otpEntity = otpRepository.findByUser(user.get());
            if (otpEntity.isPresent()) {
                Otp existingOtp = otpEntity.get();
                existingOtp.setOtpCode(otp);
                existingOtp.setCreatedAt(LocalDateTime.now());
                existingOtp.setExpiredAt(LocalDateTime.now().plusMinutes(1));
                existingOtp.setIsVerified(false);
                otpRepository.save(existingOtp);
            }
            throw new UnauthorizedException("User not verified!");
        }

        String accessToken = jwtService.generateToken(user.get());
        return TokenResponse.builder().accessToken(accessToken).email(user.get().getEmail())
                .userId(user.get().getUserId()).build();
    }

    @Override
    public UserResponse changePassword(UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("User email not found!");
        }
        if (!user.get().getType().equals("credential")) {
            throw new BadRequestException("Change password is not allowed for social login user!");
        }
        user.get().setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user.get());
        return modelMapper.map(user.get(), UserResponse.class);
    }

    @Override
    public UserResponse getUserDetails(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException("User email not found");
        }
        return modelMapper.map(user.get(), UserResponse.class);
    }

    @Override
    public UserResponse forgotPassword(ForgetRequest forgetRequest) {
        Optional<User> user = userRepository.findByEmail(forgetRequest.getEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("User email not found!");
        }
        if (!user.get().getType().equals("credential")) {
            throw new BadRequestException("Forgot password is not allowed for social login user!");
        }
        if (!forgetRequest.getNewPassword().equals(forgetRequest.getConfirmNewPassword())) {
            throw new BadRequestException("New password and confirm new password do not match");
        }
        user.get().setPassword(passwordEncoder.encode(forgetRequest.getNewPassword()));
        user.get().setIsVerified(true);
        user.get().setUpdatedAt(LocalDateTime.now());
        userRepository.save(user.get());
        return modelMapper.map(user.get(), UserResponse.class);
    }

    @Override
    public UserResponse recovery(String email) throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException("User email not found!");
        }
        if (!user.get().getType().equals("credential")) {
            throw new BadRequestException("Recovery password is not allowed for social login user!");
        }
        Integer otp = optCode();
        emailService.sendEmail(email, String.valueOf(otp));
        Optional<Otp> otpEntity = otpRepository.findByUser(user.get());
        if (otpEntity.isPresent()) {
            Otp existingOtp = otpEntity.get();
            existingOtp.setOtpCode(otp);
            existingOtp.setCreatedAt(LocalDateTime.now());
            existingOtp.setExpiredAt(LocalDateTime.now().plusMinutes(1));
            existingOtp.setIsVerified(false);
            otpRepository.save(existingOtp);
        }
        return modelMapper.map(user.get(), UserResponse.class);
    }
}
