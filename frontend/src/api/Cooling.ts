import {COOLING_MODULE} from "./_prefix";
import { axios } from '../utils/request';


//获取所有散热器
export const getAllCooling = () => {
    return axios.get(`${COOLING_MODULE}/all`, )
        .then(res => {
            return res
        })
}

//根据散热器Id获取散热器
export const getCoolingById = (coolingId: number) => {
    return axios.get(`${COOLING_MODULE}/${coolingId}`)
        .then(res => {
            return res
        })
}


