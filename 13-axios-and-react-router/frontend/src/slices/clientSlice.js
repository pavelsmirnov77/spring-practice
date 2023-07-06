import {createSlice} from "@reduxjs/toolkit";

export const clientSlice = createSlice({
    name: 'clients',
    initialState: {
        clients: []
    },
    reducers: {
        // addUser: (state, action) => {
        //     const client = action.payload;
        //     state.clients = [client, ...state.clients];
        // },
        //
        // getUserName: (state, action) => {
        //     const clientId = action.payload;
        //     const client = state.clients.find((client) => client.id === clientId);
        //     return client ? client.name : 'Unknown User';
        // },
        setClients: (state, action) => {
            state.clients = action.payload;
        },
    },
});

export const {addUser, getUserName, setClients} = clientSlice.actions;

export default clientSlice.reducer;