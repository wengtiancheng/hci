
import { axios } from '../utils/request';
import { USER_MODULE } from './_prefix';


export interface RegisterInfo {
    name: string;
    phone: string;
    password: string;
    mySolutions?: number[];
}



export const register = (registerInfo: RegisterInfo) => {
    return axios.post(`${USER_MODULE}/register`, registerInfo)
        .then(res => {
            return res
        })
}

export const login = async (phone: string, password: string) => {
    try {
        const response = await axios.post(`${USER_MODULE}/login`, null, {
            params: { phone, password }
        });
        return response.data;
    } catch (error) {
        console.error('Login failed:', error);
        throw error;
    }
};

//获取当前用户信息
export const getUserInfo = () => {
    return axios.get(`${USER_MODULE}`, )
        .then(res => {
            return res
        })
}

//获取当前用户的装机方案
export const getUserSolutions = () => {
    return axios.get(`${USER_MODULE}/solutions`, )
        .then(res => {
            return res
        })
}

export const getSolutionImages = (solutionId: number) => {
    return axios.get(`${USER_MODULE}/getSolutionImages`, {
        params: {solutionId}
    })
        .then(res => {
            return res.data.result;
        })
}


