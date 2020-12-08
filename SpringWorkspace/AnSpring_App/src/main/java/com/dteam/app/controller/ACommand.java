package com.dteam.app.controller;

import org.springframework.ui.Model;

public interface ACommand {
	public void execute(Model model);
}

//공용 command 모든 command는 Acommand를 상속받는다!
