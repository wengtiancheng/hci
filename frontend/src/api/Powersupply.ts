import {POWER_SUPPLY_MODULE} from "./_prefix";
import { axios } from '../utils/request';


export interface PowersupplyVO {
    id: number;
    name: string;
    imageUrl: string;
    price: number;
    linkUrl: string;
    brand: string;
}
export function newPowersupplyVO(): PowersupplyVO {
    return {
        id: 0,
        name: '',
        imageUrl: '',
        price: 0,
        linkUrl: '',
        brand: ''
    }
}

//获取所有电源
export const getAllPowersupply = () => {
    return axios.get(`${POWER_SUPPLY_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}

//根据电源Id获取电源
export const getPowersupplyById = (powersupplyId: number) => {
    return axios.get(`${POWER_SUPPLY_MODULE}/${powersupplyId}`)
        .then(res => {
            return res.data.result;
        })
}