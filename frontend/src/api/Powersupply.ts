import {POWER_SUPPLY_MODULE} from "./_prefix";
import { axios } from '../utils/request';

//获取所有电源
export const getAllPowerSupply = () => {
    return axios.get(`${POWER_SUPPLY_MODULE}/all`, )
        .then(res => {
            return res
        })
}

//根据电源Id获取电源
export const getPowerSupplyById = (powerSupplyId: number) => {
    return axios.get(`${POWER_SUPPLY_MODULE}/${powerSupplyId}`)
        .then(res => {
            return res
        })
}