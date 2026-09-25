package com.ems.domain.system.user.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author valarchie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserAvatarCommand {
    private Long userId;
    private String avatar;

}
