# Survival Haven Minecraft Plugin

Welcome to the Survival Haven Minecraft Plugin! This custom plugin is designed to enhance the gameplay experience on the 
Survival Haven Minecraft server (`survivalhaven.mcserver.us`) by adding useful features not available in the base game. 
This plugin is open source, and contributions are welcome.

## Features

The plugin currently has the following features:

- `/guide`: Creates a guide of particles visible only to you to guide you to a specified target.

- `/breadcrumbs`: Creates a particle trail behind you to guide you back where you came from 
(for instance, out of a deep cave).

- Crouch-mining a block made up of two slabs only breaks one of the two slabs.

- On death, you are sent a message with the coordinates of your death location.

More features are always being added in response to the desires of the player base.

## Permissions

The following permissions are available with this plugin:

`survivalhaven.guide`: Allows basic use of `/guide` to locate your current location 
or specific coordinates.

`survivalhaven.guide.player`: Allows access to `/guide player` to guide you to a player.

`survivalhaven.guide.death`: Allows access to `/guide death` to lead you to your last death.

`survivalhaven.guide.home`: Allows access to `/guide home` to guide you to one of your Essentials homes.

`survivalhaven.breadcrumbs.<limit>`: Allows access to `/breadcrumbs` with a length limit of `<limit>`.

`survivalhaven.breadcrumbs.*`: Allows access to `/breadcrumbs` with no length limit.

## Installation

To install this plugin on your Minecraft server, follow these steps:

1. Download the latest release of the plugin from the [GitHub releases page](https://github.com/FreshLlamanade/SurvivalHavenPlugin/releases).

2. Copy the `SurvivalHavenPlugin.jar` file into the `plugins` folder of your Minecraft server.

3. Restart your Minecraft server.

## Usage

To use this plugin on the Survival Haven Minecraft server, simply use the commands listed above with the appropriate 
permissions. If you encounter any issues or have suggestions for improvement, please feel free to contribute to the 
plugin's development on the [GitHub repository](https://github.com/FreshLlamanade/SurvivalHavenPlugin).
