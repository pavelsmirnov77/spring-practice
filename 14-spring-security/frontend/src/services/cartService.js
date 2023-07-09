import axios from "axios";
import authHeader from "./auth-header";
import {set} from "../slices/userSlice";
import {setCart} from "../slices/cartSlice";

const API_URL = "http://localhost:8081/cart";

const getCart = (userId, dispatch) => {

    return axios.get(API_URL + `/${userId}`,{headers: authHeader()}).then(
        (response) => {
            console.log(response.data)
            dispatch(setCart(response.data))
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });
}

const addToCart = (userId, productId, dispatch) => {

    console.log(`${API_URL}/${userId}/product/${productId}`, {quantity: 1}, {headers: authHeader()})

    return axios.post(`${API_URL}/${userId}/product/${productId}`, {quantity: 1}, {headers: authHeader()}).then(
        () => {
            getCart(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateAmount = (userId, productId, amount, dispatch) => {
    return axios.put(`${API_URL}/${userId}/product/${productId}`, amount, {headers: authHeader()}).then(
        () => {
            getCart(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteFromCart = (userId, productId, dispatch) => {

    return axios.delete(`${API_URL}/${userId}/product/${productId}`, {headers: authHeader()}).then(
        () => {
            getCart(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const clearCart = (state) => {
    state.cartItems = [];
}

const cartService = {
    getCart,
    addToCart,
    deleteFromCart,
    updateAmount,
    clearCart
};

export default cartService