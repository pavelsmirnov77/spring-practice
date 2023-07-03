package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.*;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.proxies.BankAppProxy;
import ru.sber.repositories.CartRepository;
import ru.sber.repositories.ClientRepository;
import ru.sber.repositories.ProductCartRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final CartRepository cartRepository;
    private final ProductCartRepository productCartRepository;
    private final BankAppProxy bankAppProxy;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, CartRepository cartRepository, ProductCartRepository productCartRepository, BankAppProxy bankAppProxy) {
        this.clientRepository = clientRepository;
        this.cartRepository = cartRepository;
        this.productCartRepository = productCartRepository;
        this.bankAppProxy = bankAppProxy;
    }

    @Override
    public long registrationClient(Client client) {
        Cart cart = new Cart();
        cart.setPromocode("PROMO20");
        cartRepository.save(cart);

        client.setCart(cart);
        clientRepository.save(client);

        ClientBank clientBank = new ClientBank(client.getId(), BigDecimal.ZERO);
        bankAppProxy.addClient(clientBank);
        clientBank.setBalance(BigDecimal.valueOf(10000));

        return client.getId();
    }

    @Override
    public ClientResponse getClientResponseById(long clientResponseId) {
        Optional<Client> clientOptional = clientRepository.findById(clientResponseId);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            List<ProductCart> productCarts = productCartRepository.findByCartId(client.getCart().getId());
            List<Product> products = new ArrayList<>();

            for (ProductCart productCart : productCarts) {
                Product product = productCart.getProduct();
                product.setQuantity(productCart.getQuantity());
                products.add(product);
            }

            return new ClientResponse(client.getId(), client.getName(), products);
        } else {
            throw new UserNotFoundException("Клиент с указанным id не найден");
        }
    }

    @Override
    @Transactional
    public boolean deleteClientById(long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            clientRepository.delete(client);

            return true;
        }
        else {
            throw new UserNotFoundException("Клиент с указанным id не найден");
        }
    }
}
