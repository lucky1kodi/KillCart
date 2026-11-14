# KillCart v0.7

**A Java-based filesystem management tool for Linux.**

KillCart is a command-line utility designed to help manage files and directories, particularly when deciding what to keep, move, restore, or delete.

The idea behind KillCart is simple: filesystem management should remain under human control, even when automation is impractical.

Rather than immediately deleting files, KillCart allows users to stage them for deletion and restore them when needed.

## Features

### Implemented functionality

- **Directory listing (`ls`)**
  - List files and directories.
  - Supports glob-based pattern matching.
  - `-a` includes hidden files.

- **Directory navigation (`cd`)**
  - Change the current working directory.
  - Return to the starting directory when no argument is supplied.
  - Uses path matching to locate directories.

- **Working directory (`pwd`)**
  - Display the current working directory.

- **Staged deletion (`delete`)**
  - Files and directories are renamed with a `.killcart-` prefix instead of being immediately deleted.
  - Helps prevent accidental permanent deletion.

- **File restoration (`restore`)**
  - Restore staged files by removing the `.killcart-` prefix.

- **Staged file inspection (`view`)**
  - Display staged files without their KillCart prefixes.
  - `-w` searches recursively.

- **File movement (`move`)**
  - Move or rename files and directories.
  - Supports source and destination arguments.

- **Permanent deletion (`PerminantlyDeleteAllSelectedFiles`)**
  - Permanently remove staged files.
  - `-w` enables recursive traversal.
  - Requires explicit confirmation.

- **Terminal utilities**
  - `clear` clears the terminal.
  - `help` displays available commands.
  - `exit` terminates KillCart.

## Usage

KillCart uses a semicolon (`;`) to separate command arguments.

Examples:

```text
killcart> pwd
killcart> ls
killcart> ls;*.mp3
killcart> ls;-a
killcart> cd;Music
killcart> delete;song.mp3
killcart> view
killcart> restore;song.mp3
killcart> move;song.mp3;Music
killcart> help
killcart> exit
```

### Command reference

| Command | Description |
|---|---|
| `pwd` | Print current directory |
| `ls` | List directory contents |
| `ls;-a` | Include hidden entries |
| `cd;path` | Change directory |
| `cd` | Return to starting directory |
| `delete;filename` | Stage an entry for deletion |
| `restore;filename` | Restore a staged entry |
| `view` | Display staged entries |
| `view;-w` | Recursively display staged entries |
| `move;source;destination` | Move or rename an entry |
| `PerminantlyDeleteAllSelectedFiles` | Permanently delete staged entries |
| `PerminantlyDeleteAllSelectedFiles;-w` | Recursively delete staged entries |
| `clear` | Clear terminal |
| `help` | Display command reference |
| `exit` | Exit KillCart |

## Technical details

KillCart is written in Java and primarily uses the `java.nio.file` API.

Some of the important classes and features used include:

- `Path` for filesystem paths.
- `Files` for filesystem operations.
- `Stream<Path>` for directory traversal.
- `PathMatcher` for glob-based pattern matching.
- `ArrayList<Path>` for storing matched filesystem paths.
- `Scanner` for command-line input.

The project uses absolute paths internally to simplify filesystem navigation and path handling.

The `GetFiles()` helper performs recursive filesystem traversal and uses glob patterns to select matching paths.

## Compilation and execution

Compile:

```bash
javac -cp ".:gson-2.9.1.jar" KillCart.java
```

Run:

```bash
java -cp ".:gson-2.9.1.jar" KillCart
```

`Path.of()` requires Java 11 or newer.

Gson is included in the project directory for potential future functionality, but persistent JSON storage has not yet been implemented.

## Current limitations

KillCart is still under development.

- Command parsing uses a simple semicolon-based argument separator.
- Filesystem operations and exception handling still need further testing.
- Some recursive and wildcard combinations need additional validation.
- Moving files and directories needs further testing.
- There is no persistent database or file metadata storage.
- Staged files are identified through their `.killcart-` prefix.

**Warning:** Permanent deletion cannot be undone. Test destructive operations on a dummy filesystem before using them on important files.

## Development direction

KillCart is being developed in stages.

The current focus is completing the basic filesystem-management functionality, improving path handling, supporting wildcard selection consistently, and testing the existing commands.

Future development may include persistent metadata storage, improved recovery information, and storage calculations.

---

*KillCart v0.7 — A human-controlled approach to filesystem management.*