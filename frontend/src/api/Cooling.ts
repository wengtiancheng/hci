import {COOLING_MODULE} from "./_prefix";
import { axios } from '../utils/request';


export interface CoolingVO {
    id: number;
    name: string;
    imageUrl: string;
    price: number;
    linkUrl: string;
    brand: string;
}

export function newCoolingVO(): CoolingVO {
    return {
        id: 0,
        name: '',
        imageUrl: '',
        price: 0,
        linkUrl: '',
        brand: ''
    }
}


//获取所有散热器
export const getAllCooling = () => {
    return axios.get(`${COOLING_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}

//根据散热器Id获取散热器
export const getCoolingById = (coolingId: number) => {
    return axios.get(`${COOLING_MODULE}/${coolingId}`)
        .then(res => {
            return res.data.result;
        })
}


