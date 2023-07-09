import axios from 'axios';
import UserService from './userService';
import CartService from './cartService';

const API_URL = 'http://localhost:8081/payment';

const pay = (payment, dispatch) => {
    return axios
        .post(API_URL, payment)
        .then(() => {
            UserService.getUser(payment.userId, dispatch);
            CartService.clearCart(payment.userId, dispatch);
        })
        .catch((error) => {
            const _content = (error.response && error.response.data) || error.message || error.toString();
            console.error(_content);
            throw error;
        });
};

const paymentService = {
    pay,
};

export default paymentService;