include(
    "infrastructure:vertx",
    "infrastructure:flyway",
    "common:exceptions",
    "common:extensions",
    "backend:transaction:adapters",
    "backend:transaction:app",
    "backend:transaction:domain",
    "backend:account:adapters",
    "backend:account:app",
    "backend:account:domain",
    "backend:mono-log:adapters",
    "backend:mono-log:app",
    "backend:mono-log:domain"
)

project(":backend:account:app").name = "jvm-account-api"
project(":backend:transaction:app").name = "jvm-transaction-api"
project(":backend:mono-log:app").name = "jvm-mono-log-api"

rootProject.name = "xepelin-bank"
