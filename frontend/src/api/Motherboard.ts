import {MOTHERBOARD_MODULE} from "./_prefix";
import { axios } from '../utils/request';



export interface MotherboardVO {
    id: number;
    name: string;
    imageUrl: string;
    price: number;
    linkUrl: string;
    type: string;
    brand: string;
    memoryType: string;
}
export function newMotherboardVO(): MotherboardVO {
    return {
        id: 0,
        name: '',
        imageUrl: '',
        price: 0,
        linkUrl: '',
        type: '',
        brand: '',
        memoryType: ''
    }
}

//获取所有主板
export const getAllMotherboard = () => {
    return axios.get(`${MOTHERBOARD_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}

//根据主板Id获取主板
export const getMotherboardById = (motherboardId: number) => {
    return axios.get(`${MOTHERBOARD_MODULE}/${motherboardId}`)
        .then(res => {
            return res.data.result;
        })
}