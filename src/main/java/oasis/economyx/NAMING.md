# Basic naming principles:

Main directories: plural (e.g. classes)

Sub directories: singular (e.g. actor)

Final types: The obvious semantic name (e.g. Exchange)

Interfaces: Some variation of the semantic name (e.g. MarketHost)

Exception: When the instantiable class is nested inside the interface.
For example: The interface Vote (in oasis.economyx.interfaces.voting)

**Basically, the type that will be used the most within the code takes the semantic name.**

Vote is an interface using the obvious name, while the nested class Ballot is the actual instance.
This is because the class Ballot is never referenced outside the Vote file.
Instantiation happens via static getters in the interface Vote.

For external users, Vote will seem to be instantiable.

**DO NOT USE the Impl suffix. I hate it.**