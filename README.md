# EconomyX
#### For Bukkit economy servers
_(Migrated from Sponge)_

### Latest version: 1.0 _(unfinished)_

EconomyX provides a versatile backbone for any type of economy server.
A basic command interface is provided by default, but a custom implementation is required for more advanced UIs.
Code is commented in English.

### Assets
EconomyX assets are capable of representing any kind of ownership.
From basic cash to complex financial derivatives, any combination of assets can be structured into a tradable vehicle.
All assets have one classification and a UUID for individual type discrimination.
Display names are planned to be coded in.

Basic asset types:
- Cash
- Commodity (represented by an in-game item: [Commodity.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftypes%2Fasset%2Fcommodity%2FCommodity.java))
- Stock (issued by a shared actor: [Shared.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Finterfaces%2Factor%2Ftypes%2Fownership%2FShared.java))
- Property (a claim on in-game land: [Property.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftypes%2Fasset%2Fproperty%2FProperty.java))

Contracts (A right to receive an asset in the future):
- Option (any asset can be used as the underlying: [Option.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftypes%2Fasset%2Fcontract%2Foption%2FOption.java))
- Collateral (used to represent a potential liability)
- Forward (aka futures: [Forward.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftypes%2Fasset%2Fcontract%2Fforward%2FForward.java))
- Note (aka bond: [Note.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftypes%2Fasset%2Fcontract%2Fnote%2FNote.java))
- Swap (a cash settled contract with a value derived from the difference between two assets' cash flows: [Swap.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftypes%2Fasset%2Fcontract%2Fswap%2FSwap.java))

Here's an example for stacking assets together to create a complex derivative.
Let A be the stock of some company. We then create a forward contract B with A as the underlying.
Then we use B as an underlying for an option contract C.

This option contract C is then used as a base asset with the quote asset being another asset in a swap agreement D.
Then some exchange lists this swap D.

Now anyone can buy and sell D simply by placing an order!

### Actors
Actors are the base class for anything capable of executing economic action.
The current type hierarchy is as follows. (i: interface, a: abstract class, **bold**: final)

- Actor (i)
  - EconomicActor (a)
    - Sovereignty (a)
      - **Republic**
      - **Principality**
      - **Empire**
      - **Federation**
    - Institution (a) _Institutions belong to a sovereign_
      - **Legislature**
      - **CentralBank**
      - **ResearchCenter**
      - **Administration**
      - **Military**
      - **Judiciary**
      - **Mint**
    - **Trust**
    - AbstractOrganization (a)
      - **Alliance** _An organization of sovereigns_
      - **Party** _An organization of people_
      - **Cartel** _An organization of corporations_
    - **NaturalPerson**
    - Company (a)
      - **Exchange** _Any asset can be listed in an exchange_
      - **HoldingsCompany**
      - **ConstructionCompany**
      - **Bank** _Any asset can be deposited_
      - **Merchant** _Can accept credit card payments_
      - **SecuritiesBroker** _Can broker orders sent to exchanges_
      - **Manufacturer**
      - **Mercenary** _Able to participate in wars as an independent faction_
      - **AuctionHouse** _Can host auctions_
      - **Distillery**
      - **Guarantor** _Can offer guarantees_
      - **PaperMill**
      - **LawFirm**
      - **VaultCompany**

### Price Providers
Price providers offer fair pricing of assets by means of market forces.
These prices are used to derive the value of options and swap agreements.

#### Markets
Markets([Marketplace.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Finterfaces%2Ftrading%2Fmarket%2FMarketplace.java))
are a subtype of PriceProvider, and offer pricing via buy/sell orders.
Every major order type is supported,
including complex types such as **FoK**(Fill or kill) and **IoC**(Immediate or cancel).
Orders are automatically processed in regular intervals.
See [MarketTickTask.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftasks%2Ftrading%2FMarketTickTask.java)

#### Auctions
Auctions([Auctioneer.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Finterfaces%2Ftrading%2Fauction%2FAuctioneer.java))
are a subtype of PriceProvider, and offer pricing via buyers' bids.
Auctions are processed automatically in regular intervals.
See [AuctionTickTask.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftasks%2Ftrading%2FAuctionTickTask.java)

### Implementing EconomyX
- Forking this repository and adding in any code you desire is recommended.
- If you want to use this code from another plugin, add EconomyX as a dependency.

### Locale
Default locale is Korean. If you wish to use another language, you will need to change the following files/directories.
(There may be more)
All localization is done via constant variables for easy configuration. (with the exception of format() methods)

- [voting](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Fclasses%2Fvoting)
- [EconomyCommand.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Fcommands%2FEconomyCommand.java)
- [CreateCommand.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Fcommands%2Fcreate%2FCreateCommand.java)
- [management](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Fcommands%2Fmanagement)
- [VoteCommand.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Fcommands%2Fvoting%2FVoteCommand.java)
- [HostilityCommand.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Fcommands%2Fwarfare%2FHostilityCommand.java)
- [address](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftypes%2Faddress)
- [asset](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftypes%2Fasset)
- [Offer.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Ftypes%2Foffer%2FOffer.java)
- [TaxType.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Fevents%2Ftax%2FTaxType.java)
- [Vote.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Finterfaces%2Fvoting%2FVote.java)
- [OfferEventListener.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Flisteners%2Foffer%2FOfferEventListener.java)
- [PaymentListener.java](src%2Fmain%2Fjava%2Foasis%2Feconomyx%2Flisteners%2Fpayment%2FPaymentListener.java)