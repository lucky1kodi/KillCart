# KillCart v0.6

A Java-based command-line filesystem management tool designed to make managing personal file libraries easier.

KillCart allows users to stage files for deletion, restore them when needed, move or rename files, and permanently delete staged files.

## Features

- **File navigation:** Browse directories using `ls`, `cd`, and `pwd`.
- **Staged deletion:** Files and directories are renamed with a `.killcart-` prefix instead of being immediately deleted.
- **Restoration:** Restore staged files and directories to their original names.
- **File inspection:** View staged files using `view`.
- **Permanent deletion:** Permanently delete staged files after explicit confirmation.
- **Recursive traversal:** Support for recursively viewing and permanently deleting staged files.
- **Move and rename:** Move files and directories or rename them using `move`.
- **Wildcard selection:** Initial implementation using Java's `PathMatcher` and glob patterns. Integration into commands is still in progress.

## Compilation

```bash
javac -cp ".:gson-2.9.1.jar" KillCart.java
```

Gson is not currently required by the implementation.

## Running

```bash
java KillCart
```

## Commands

| Command | Description |
|---|---|
| `pwd` | Display current directory |
| `ls` | List directory contents |
| `cd ;path` | Change directory |
| `view` | View staged files |
| `delete ;filename` | Stage a file or directory for deletion |
| `restore ;filename` | Restore a staged file or directory |
| `move ;source;destination` | Move or rename files/directories |
| `PerminantlyDeleteAllSelectedFiles` | Permanently delete staged files |
| `clear` | Clear terminal |
| `help` | Display available commands |
| `exit` | Exit KillCart |

Use semicolons (`;`) to separate command arguments.

## Important

KillCart is currently under development. Avoid testing destructive operations on important files or directories. Use a dummy filesystem until the implementation has been thoroughly tested.