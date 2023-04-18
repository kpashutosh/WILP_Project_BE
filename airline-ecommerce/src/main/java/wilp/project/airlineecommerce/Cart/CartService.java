package wilp.project.airlineecommerce.Cart;

import java.util.List;

public interface CartService {
	
	Cart createCart(Cart cart);
	
	List<Traveller> addPassengers(List<Traveller> travellers, String cartId);

	Cart getCartById(String cartId);

	List<Traveller> getPassengersFromCart(String cartId);

}
