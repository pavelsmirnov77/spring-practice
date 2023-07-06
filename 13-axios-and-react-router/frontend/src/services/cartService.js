import axios from "axios";
import {setCartProducts} from "../slices/cartSlice";

const API_URL = "http://localhost:8081/carts";

export const getCartProducts = (cartId) => {
    return (dispatch) => {
        return axios.get(`${API_URL}/${cartId}`)
            .then((response) => {
                dispatch(setCartProducts(response.data));
            })
            .catch((error) => {
                const errorMessage = (error.response && error.response.data) || error.message || error.toString();
                console.error(errorMessage);
                dispatch(setCartProducts([]));
            });
    };
};

export const addProductToCart = (cartId, productId) => {
    return (dispatch) => {
        return axios.post(`${API_URL}/${cartId}/${productId}`)
            .then(() => {
                dispatch(getCartProducts(cartId));
            })
            .catch((error) => {
                const errorMessage = (error.response && error.response.data) || error.message || error.toString();
                console.error(errorMessage);
            });
    };
};

export const updateCartItemQuantity = (cartId, productId, quantity) => {
    return (dispatch) => {
        return axios.put(`${API_URL}/${cartId}/${productId}?quantity=${quantity}`)
            .then(() => {
                dispatch(getCartProducts(cartId));
            })
            .catch((error) => {
                const errorMessage = (error.response && error.response.data) || error.message || error.toString();
                console.error(errorMessage);
            });
    };
};

export const removeFromCart = (cartId, productId) => {
    return (dispatch) => {
        return axios.delete(`${API_URL}/${cartId}/${productId}`)
            .then(() => {
                dispatch(getCartProducts(cartId));
            })
            .catch((error) => {
                const errorMessage = (error.response && error.response.data) || error.message || error.toString();
                console.error(errorMessage);
            });
    };
};

export const makePayment = (cartId) => {
    return (dispatch) => {
        return axios.post(`${API_URL}/${cartId}/payment`)
            .then((response) => {
                const paymentStatus = response.data.status;
                if (paymentStatus === "success") {
                    dispatch(getCartProducts(cartId));
                } else {
                    throw new Error("Ошибка оплаты");
                }
            })
            .catch((error) => {
                const errorMessage = (error.response && error.response.data) || error.message || error.toString();
                console.error(errorMessage);
            });
    };
};

const cartService = {
    getCartProducts,
    addProductToCart,
    updateCartItemQuantity,
    removeFromCart,
    makePayment
};

export default cartService;
