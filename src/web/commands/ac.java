package web.commands;

public class ac {
		
	public ac() {
		//case "send":
			cmd = args[1].split(" ", 2);
			if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
				ProcessPlus process = pmg.getProcess(cmd[0]);
				process.send(cmd[1]);
				return;
			} else {
				return;
			}
		//case "read":
			if (pmg.hasProcess(args[1])) {
				ProcessPlus process = pmg.getProcess(args[1]);
				String console = '@'+process.readInput(100);
				webServer.sendRequet(out, console.getBytes("UTF8"), "console");
				return;
			} else {
				return;
			}
	}
			
}