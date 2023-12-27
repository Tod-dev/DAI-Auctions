# DAI-Auctions
Auctions between agents using lighTS Tuple Space.
## SIMULATION:
Launch simulation (1 Auctioneer and 3 Agents) (Only for MACOS)
```
./simulation 
```
## INSTRUCTIONS:
### (all parameters are optional)
1: build all classes
```
./build.sh
```
2: launch the tuple space rmi registry
```
./run.sh RemoteTupleSpace <tupleSpaceName>
```
3: launch as many clients as you want
```
./run.sh AuctionParticipant <name> <tupleSpaceName>
```
4: launch the auctioneer
```
./run.sh Auctioneer <tupleSpaceName>  
```
