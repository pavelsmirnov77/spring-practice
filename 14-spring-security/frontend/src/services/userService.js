import axios from "axios";
import {set} from "../slices/userSlice";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8081/users";

const getUser = (id, dispatch) => {
    return axios.get(API_URL + `/${id}`,{headers: authHeader()}).then(
        (response) => {
            dispatch(set(response.data));
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });

};

const userService = {
    getUser,
};

export default userService