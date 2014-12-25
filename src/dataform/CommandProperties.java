package dataform;

public class CommandProperties {

	public boolean useTarget;
	public boolean opsOnly;
	public boolean allowConsole;
	public boolean allowOffineTarget;
	public boolean targetRequired;
	
	public CommandProperties(boolean useTarget, boolean opsOnly, boolean allowConsole, boolean allowOfflineTarget, boolean targetRequired) {
		this.useTarget = useTarget;
		this.opsOnly = opsOnly;
		this.allowConsole = allowConsole;
		this.allowOffineTarget = allowOfflineTarget;
		this.targetRequired = targetRequired;
	}
	
}
