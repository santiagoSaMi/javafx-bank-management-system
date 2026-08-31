# JavaFX Bank Management System

A desktop banking application built with **Java** and **JavaFX**, using **Scene Builder** for UI design. The system simulates core banking operations — client management, financial products, and transactions — through a multi-screen graphical interface.

> Originally developed as an academic project; uploaded here to showcase the codebase and UI design work.

## Features

- **Client management** — register and browse clients, each linked to a set of financial products (savings account, checking account, CDT, credit cards)
- **Product management** — create and view financial products tied to a specific client
- **Transactions** — deposits, withdrawals, cash advances, purchases, and password changes, with client authentication before each operation
- **Multi-screen navigation** — separate FXML views for login, client listing, product listing, and transactions, wired together with FXML controllers
- **File-based persistence** — client and product data is stored and loaded from local text files

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 19 |
| UI Framework | JavaFX (FXML) |
| UI Design Tool | Scene Builder |
| Build Tool | Ant (NetBeans project) |
| IDE | NetBeans |
| Data Storage | Flat-file (`.txt`) persistence |

## Project Structure

```
src/
├── control/     # FXML controllers (UI event handling, navigation)
├── negocio/     # Domain model (Cliente, Producto)
├── gestion/     # Business logic / data access (GestionClientes, GestionProductos)
├── vista/       # FXML view files (Scene Builder layouts)
└── start/       # Application entry point

Archivos/        # Sample data files (clients, products)
images/          # UI assets
```

## Architecture

The project follows a layered structure inspired by MVC:
- **`vista/`** — FXML layouts defining the visual interface
- **`control/`** — Controllers that handle UI events and screen transitions
- **`negocio/`** — Plain domain objects representing clients and products
- **`gestion/`** — Logic layer responsible for reading/writing and managing data

## Running the Project

This is a NetBeans (Ant) project.

1. Clone the repository
2. Open the project folder in **NetBeans** (or any Ant-compatible IDE)
3. Ensure a JDK compatible with Java 19 is configured
4. Run `Start.java`, or execute `ant run` from the project root

## Notes

The included data files under `Archivos/` contain fictional sample data used for testing and demonstration purposes only.
