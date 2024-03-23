import axios from "axios";
import {set} from "../slices/usersSlice";
import {setCart} from "../slices/productsCartSlice";

const API_URL = "http://localhost:8081/user";

const getUser = (id, dispatch) => {
    return axios.get(API_URL + `/${id}`).then(
        (response) => {
            dispatch(set(response.data));
            dispatch(setCart(response.data.cart))
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });

};

const register = (registration) => {
    const {name, email, login, password} = registration;
    return axios.post(API_URL, {
        name,
        email,
        login,
        password,
    });
};


const authorize = (loginData, dispatch) => {
    return axios.get(API_URL, {params: loginData}).then(
        (response) => {
            const userId = response.data;
            localStorage.setItem("userId", userId);
            getUser(userId, dispatch);
        },
        (error) => {
            const _content =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content);

            dispatch(set([]));
        }
    );
}

const logout = () => {
    console.log("Выход из аккаунта")
    localStorage.removeItem("users");
    localStorage.removeItem("userId")
};


const userService = {
    getUser,
    authorize,
    register,
    logout
};

export default userService