package com.tola.demoapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskRequest {
    private Long projectId;
    private Long taskId;
    private List<Long> userIds;
}
