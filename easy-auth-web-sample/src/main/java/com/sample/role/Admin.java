package com.sample.role;

import com.tyytogether.annotation.EasyAuthRole;

@EasyAuthRole(permissions = {"changeUserAmount","viewWebsiteStatistics"})
public class Admin {
}
