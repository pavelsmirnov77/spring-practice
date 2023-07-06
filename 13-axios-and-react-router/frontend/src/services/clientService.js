import axios from "axios";
import {setClients} from "../slices/clientSlice";

const API_URL = "http://localhost:8081/clients";

export const getClient = () => {
    return (dispatch) => {
        return axios.get(API_URL)
            .then((response) => {
                dispatch(setClients(response.data));
            })
            .catch((error) => {
                const errorMessage = (error.response && error.response.data) || error.message || error.toString();
                console.error(errorMessage);
                dispatch(setClients([]));
            });
    };
};

export const addClient = (client) => {
    return (dispatch) => {
        return axios.post(API_URL, client)
            .then((response) => {
                dispatch(getClient());
            })
            .catch((error) => {
                const errorMessage = (error.response && error.response.data) || error.message || error.toString();
                console.error(errorMessage);
            });
    };
};

export const deleteClient = (id) => {
    return (dispatch) => {
        return axios.delete(API_URL + `/${id}`)
            .then((response) => {
                dispatch(getClient());
            })
            .catch((error) => {
                const errorMessage = (error.response && error.response.data) || error.message || error.toString();
                console.error(errorMessage);
            });
    };
};

const clientService = {
    getClient,
    addClient,
    deleteClient
};

export default clientService