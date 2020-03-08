package web.commands;

public class ac {
		
	public ac() {
		case "timestopnoforce":
			Main.timeStopNoForce = Long.parseLong(args[1]);
			Main.dateRestart = Main.dateRestart(-Main.timeStopNoForce).getTime();
			Main.saveData();
			break;
		case "stopnoforceserver":
			webServer.getProcessManagerServer().stopNoForce(true);
			break;
		case "stopnoforcenoscriptserver":
			webServer.getProcessManagerServer().stopNoForce(false);
			break;
		case "stopscriptserver":
			webServer.getProcessManagerServer().stopScript();
			break;
		case "stopserver":
			webServer.getProcessManagerServer().stop();
			break;
		case "cmdend":
			Main.cmdEnd = args[1];
			Main.saveData();
			break;
		case "onStart":
			if (pmg.hasProcess(args[1])) {
				ProcessPlus process = pmg.getProcess(args[1]);
				process.setActiveOnStart(!process.isActiveOnStart());
				pmg.saveProcces(process.getName());
			}
			break;
		case "start":
			if (pmg.hasProcess(args[1]) && !webServer.getProcessManagerServer().isStopNoForce()) {
				ProcessPlus process = pmg.getProcess(args[1]);
				process.reload();
				process.getProcess().start();
			}
			break;
		case "restart":
			if (pmg.hasProcess(args[1]) && !webServer.getProcessManagerServer().isStopNoForce()) {
				ProcessPlus process = pmg.getProcess(args[1]);
				process.getProcess().stop();
				process.reload();
				process.getProcess().start();
			}
			break;
		case "stop":
			if (pmg.hasProcess(args[1])) {
				ProcessPlus process = pmg.getProcess(args[1]);
				process.getProcess().stop();
			}
			break;
		case "cmd":
			cmd = args[1].split(" ", 2);
			if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
				ProcessPlus process = pmg.getProcess(cmd[0]);
				process.setCmd(cmd[1]);
				pmg.saveProcces(process.getName());
			}
			break;
		case "cmdstop":
			cmd = args[1].split(" ", 2);
			if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
				ProcessPlus process = pmg.getProcess(cmd[0]);
				process.setCmdStop(cmd[1]);
				pmg.saveProcces(process.getName());
			}
			break;
		case "rep":
			cmd = args[1].split(" ", 2);
			if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
				ProcessPlus process = pmg.getProcess(cmd[0]);
				process.setRep(cmd[1]);
				pmg.saveProcces(process.getName());
			}
			break;
		case "send":
			cmd = args[1].split(" ", 2);
			if (cmd.length == 2 && pmg.hasProcess(cmd[0])) {
				ProcessPlus process = pmg.getProcess(cmd[0]);
				process.send(cmd[1]);
				return;
			} else {
				return;
			}
		case "read":
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