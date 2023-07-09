import axios from "axios";
import UserService from "./userService";
import authHeader from "./auth-header";
import CartService from "./cartService";

const API_URL = "http://localhost:8081/payment";

const pay = (payment, dispatch) => {

    return axios.post(API_URL, payment, {headers: authHeader()}).then(
        () => {
            UserService.getUser(payment.userId, dispatch)
            CartService.clearCart(payment.userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const paymentService = {
    pay,
};

export default paymentService