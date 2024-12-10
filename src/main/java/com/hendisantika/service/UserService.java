package com.hendisantika.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, String> users = new HashMap<>(); // Sử dụng HashMap để lưu người dùng (thực tế có thể lưu vào cơ sở dữ liệu)

    // Đăng ký người dùng
    public boolean register(String username, String email, String password) {
        if (users.containsKey(username)) {
            return false; // Người dùng đã tồn tại
        }
        users.put(username, password); // Lưu người dùng mới
        return true;
    }

    // Đăng nhập người dùng
    public boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password); // Kiểm tra đăng nhập
    }
}
