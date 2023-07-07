import {createSlice} from '@reduxjs/toolkit'

const user = JSON.parse(localStorage.getItem("user"));

const initialState = user
    ? {isLoggedIn: true, user}
    : {isLoggedIn: false, user: null};

export const authSlice = createSlice({
    name: 'auth',
    initialState: initialState,
    reducers: {
        login: (state, action) => {
            state.isLoggedIn = true;
            state.user = action.payload;
        },
        logout: (state, action) => {
            state.isLoggedIn = false;
            state.user = null;
        }
    },
})

// Action creators are generated for each case reducer function
export const {login, logout} = authSlice.actions

export default authSlice.reducer