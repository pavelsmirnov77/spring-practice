import axios from "axios";
import {set} from "../slices/usersSlice";
import {setCart} from "../slices/productsCartSlice";

const API_URL = "http://localhost:8081/users";

const getUser = (id, dispatch) => {
    return axios.get(API_URL + `/${id}`).then(
        (response) => {
            dispatch(set(response.data));
            dispatch(setCart(response.data.carts))
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });

};

const authorize = (loginData, dispatch) => {
    return axios.get(API_URL, {params: loginData}).then(
        (response) => {
            getUser(response.data, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });
}


const userService = {
    getUser,
    authorize,
};

export default userService