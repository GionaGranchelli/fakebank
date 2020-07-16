import {createSlice} from '@reduxjs/toolkit';

export const apiReducer = createSlice({
    name: 'api',
    initialState: {
        status: undefined,
        records: [],
        error: undefined
    },
    reducers: {
        loadRecords: state => {
            state.status = REQUEST_SENT;
        },
        responseSuccess: (state, action) => {
            state.status = RESPONSE_SUCCESS;
            state.records = action.payload;
        },
        responseFail: (state, action) => {
            state.status = RESPONSE_FAIL;
            state.error = action.payload;
        },
    },
});

export const {loadRecords, responseSuccess, responseFail} = apiReducer.actions;
const REQUEST_SENT = 'REQUEST_SENT';
const RESPONSE_SUCCESS = 'RESPONSE_SUCCESS';
const RESPONSE_FAIL = 'RESPONSE_FAIL';
