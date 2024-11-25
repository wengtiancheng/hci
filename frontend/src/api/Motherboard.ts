import {MOTHERBOARD_MODULE} from "./_prefix";
import { axios } from '../utils/request';

//获取所有主板
export const getAllMotherboard = () => {
    return axios.get(`${MOTHERBOARD_MODULE}/all`, )
        .then(res => {
            return res
        })
}

//根据主板Id获取主板
export const getMotherboardById = (motherboardId: number) => {
    return axios.get(`${MOTHERBOARD_MODULE}/${motherboardId}`)
        .then(res => {
            return res
        })
}