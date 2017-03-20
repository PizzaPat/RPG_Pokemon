/**
 * Addition Event Class
 */
public class AdditionEvent {

    /**
     * Pokeshop to trade items
     * @param player ash
     */
    public static void pokeshop(Player player){
        System.out.println("You've arrived at Pokeshop, would you like to trade anything?");
        int choice = 0;
        while(choice!=4){
            System.out.println("1. Buy Potion (2 Pokecoins) \n2. Buy Pokeball (5 Pokecoins)\n" +
                    "3. Heal All Pokemons\n4. Exit The Shop");
            choice = CheckInput.checkIntRange(1,4);
            switch (choice){
                case 1: amountPurchased(player,1); break;
                case 2: amountPurchased(player,2); break;
                case 3: player.healAllPokemon(); break;
                default: break;
            }
        }
    }

    /**
     * Specify the amount of purchase
     * @param player ash
     * @param category category 1 for potion, and category 2 for pokeball
     */
    public static void amountPurchased(Player player, int category){
        System.out.print("Quantity: ");
        int quantity = CheckInput.checkInt();
        for(int i=0; i<quantity; i++){
            if(category==1){
                player.buyPotion();
            }
            else{
                player.buyPokeball();
            }
        }
    }
}
