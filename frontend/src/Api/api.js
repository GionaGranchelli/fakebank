const BASE_URL = "http://localhost";
const PORT = "8080";

export const postFileToValidate = formData => {
    const endpoint = 'loadStatement';
    const url = BASE_URL + ':' + PORT + '/' + endpoint;
    return fetch(url, {
        method: "POST",
        body: formData,
        mode: 'cors'
    }).then(response => response.json());
};