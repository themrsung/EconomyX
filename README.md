# EconomyX
#### For Bukkit economy servers
(Migrated from Sponge)

EconomyX provides a versatile backbone for any type of economy server.
As there is no built-in front end, a custom UI is required for it to be a functional product.
This can be a big time saver for server developers who want to focus on the front-end part of their server.

Due to its complex nature, EconomyX is completely independent to Sponge's integrated economy API.

### Features
- Basically every asset type in existence ([asset](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Fasset))
- Corporations, Institutions, Sovereignties - All created and managed by users
- Multiple currencies supported
- Automated contract fulfillment
- Markets and auctions ([trading](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftrading))
- Options contracts dependent on market prices
- Employment and automated salary payment ([Employer.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Factor%2Ftypes%2FEmployer.java))
- Cash-settled swaps ([Swap.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Fasset%2Fcontract%2Fswap%2FSwap.java))
- Casinos ([House.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Finterfaces%2Factor%2Ftypes%2Fservices%2FHouse.java))

### Implementing EconomyX
- Forking this repository and adding in any code you desire is recommended.
- If you want to use this code from another plugin, add EconomyX as a dependency.