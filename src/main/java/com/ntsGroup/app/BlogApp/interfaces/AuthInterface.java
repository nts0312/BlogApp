package com.ntsGroup.app.BlogApp.interfaces;

import com.ntsGroup.app.BlogApp.dto.LoginDto;
import com.ntsGroup.app.BlogApp.dto.RegisterDto;

public interface AuthInterface {
	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);
}
