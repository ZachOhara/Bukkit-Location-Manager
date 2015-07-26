# Bukkit Location Manager

The Bukkit Location Manager is a plugin for a Bukkit server that adds a few useful tools for accessing and managing the locations of players on the server. It is last confirmed to work on Bukkit version 1.7.9-R2. A full list of commands that this plugin adds is included below.

This plugin uses my Bukkit Common Library. The server *must* have that plugin installed for this plugin to work. The Bukkit Common Library can be downloaded from [GitHub](http://github.com/zachohara/bukkit-common)

Along with all of the source code, in the root folder of this repository you'll find [detailed documentation](javadoc) and a compiled .jar version of the project.

I may or may not support this software in the future, but feel free to send a pull request if you think you have a way to improve it. There is no warranty on this software, and I am absolutely not going to do full-time tech support for it, but I will try to be as helpful as I can if you're having problems. Send me an email, or create a new issue.

This entire repository is made available under the GNU General Public License v3.0. A full copy of this license is available as the [LICENSE](LICENSE) file in this repository, or at [gnu.org/licenses](http://www.gnu.org/licenses/).

## Installation

Download the "Location Manager v___.jar" from the root folder of this repository, or check out the [releases page](https://github.com/ZachOhara/Bukkit-Location-Manager/releases) and download the latest version. Drop either file into the 'plugins' folder on your server.

## Added Commands:

### GetLocation

Usage: `/getlocation <player>` or `/getloc <player>` or `/gl <player>`

This command is usable only by players with admin privileges, or directly by the console. It will send you the current coordinates of the given player if they are online, or the coordinates of their last known position if they are offline.

### RequestLocation

Usage: `/requestlocation <player>` or `/requestloc <player>` or `/reqloc <player>`

This command will request that the given player send you their location. That player can then respond by using the TellLocation command, which will inform you of their location.

### TellLocation

Usage: `/telllocation [player]` or `/tellloc [player]` or `/tl [player]`

This command will send your current coordinates to the given player. If no other player is specified, your location will be send to the last person that requested your location using the RequestLocation command.

### BroadcastLocation

Usage: `/broadcastlocation [player]` or `/broadcastloc [player]` or `/broadloc [player]` or `/bl [player]`

This command, if send without any arguments, will broadcast the coordinates of your location to every other player on the server. If an target player is given, and the sender is an OP on the server, the location of the specified player will be broadcast to all other players on the server.

### MyLocation

Usage: `/mylocation` or `/myloc` or `/ml`

This command will return your own coordinates to you. It's not necessarily much more useful than using the built-in debug menu to look at your coordinates, but it's useful to reference when typing other commands.
