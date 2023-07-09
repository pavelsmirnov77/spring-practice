import axios from "axios";
import UserService from "./userService";
import {setCart} from "../slices/productsCartSlice";

const API_URL = "http://localhost:8081/cart";

const addToCart = (userId, productId, dispatch) => {

    return axios.post(`${API_URL}/${userId}/product/${productId}`, {quantity: 1}).then(
        () => {
            UserService.getUser(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateAmount = (userId, productId, quantity, dispatch) => {
    return axios.put(`${API_URL}/${userId}/product/${productId}`, quantity).then(
        () => {
            UserService.getUser(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteFromCart = (userId, productId, dispatch) => {
    return axios.delete(`${API_URL}/${userId}/product/${productId}`)
        .then(() => {
            UserService.getUser(userId, dispatch);
        })
        .catch((error) => {
            const _content =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content);
        });
};

const clearCart = () => {
    return (dispatch) => {
        dispatch(setCart([]));
    };
}


const cartService = {
    addToCart,
    deleteFromCart,
    updateAmount,
    clearCart
};

export default cartService