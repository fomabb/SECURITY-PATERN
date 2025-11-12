package com.iase24.crazy_task_tracker_api.entity.enumerate;

public enum GroupChatType {
    OPEN,         // Открытая, вступить может любой
    BY_REQUEST,   // По заявке, требуется подтверждение админа
    PRIVATE       // Закрытая, пригласить может только админ
}
