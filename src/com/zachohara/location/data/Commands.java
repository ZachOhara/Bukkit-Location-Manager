package com.zachohara.location.data;

import com.zachohara.location.dataform.CommandProperties;

public interface Commands {

	public CommandProperties GET = new CommandProperties(true, true, true, true, true);
	public CommandProperties REQUEST = new CommandProperties(true, false, false, false, true);
	public CommandProperties ACCEPT = new CommandProperties(false, false, false, false, false);
	public CommandProperties DENY = new CommandProperties(false, false, false, false, false);
	public CommandProperties TELL = new CommandProperties(true, false, false, false, true);
	public CommandProperties BROADCAST = new CommandProperties(true, false, true, true, false);
	public CommandProperties ME = new CommandProperties(false, false, false, false, false);
	public CommandProperties HELP = new CommandProperties(false, false, true, false, false);
	
}
