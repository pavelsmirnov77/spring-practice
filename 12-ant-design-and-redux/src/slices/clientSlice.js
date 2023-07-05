import {createSlice} from "@reduxjs/toolkit";

export const clientSlice = createSlice({
    name: 'clients',
    initialState: {
        clients: [
            {
                id: 1,
                name: 'Павел',
                login: 'pavel123',
                password: '123',
                email: 'pavel@yandex.ru'
            },
            {
                id: 2,
                name: 'Виталий',
                login: 'vitaliy456',
                password: '456',
                email: 'vitaliy@gmail.com'
            },
            {
                id: 3,
                name: 'Коля',
                login: 'kolya789',
                password: '789',
                email: 'kolya@mail.ru'
            }
        ],
    },
    reducers: {
        addUser: (state, action) => {
            const client = action.payload;
            state.clients = [client, ...state.clients];
        },

        getUserName: (state, action) => {
            const clientId = action.payload;
            const client = state.clients.find((client) => client.id === clientId);
            return client ? client.name : 'Unknown User';
        },
    },
});

export const {addUser, getUserName} = clientSlice.actions;

export default clientSlice.reducer;