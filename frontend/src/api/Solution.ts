import {SOLUTION_MODULE} from "./_prefix";

import { axios } from '../utils/request';
import OpenAI from "openai";

export enum SortType {
    NONE = 'NONE',
    PRICE_DESC = 'PRICE_DESC',
    SAVE_DESC = 'SAVE_DESC',
    CREATE_TIME_DESC = 'CREATE_TIME_DESC'
}
export interface SolutionVO {
    id: number;
    name: string;
    imageUrl: string;
    totalPrice: number;
    description: string;
    saveNum: number;
    createTime: Date;
    cpuId: number;
    motherboardId: number;
    gpuId: number;
    memoryId: number;
    harddiskId: number;
    powersupplyId: number;
    coolingId: number;
    chassisId: number;
    displayId: number;
    cpuName?: string;
    gpuName?: string;
    images?: string[];
    componentNames?: string[];
}

// 初始化 SolutionVO
export function newSolutionVO(): SolutionVO {
    return {
        id: 0, // 默认 ID
        name: '', // 默认空字符串
        imageUrl: '', // 默认空字符串
        totalPrice: 0, // 默认 0
        description: '', // 默认空字符串
        saveNum: 0, // 默认 0
        createTime: new Date(), // 当前时间
        cpuId: 0, // 默认 CPU ID
        motherboardId: 0, // 默认主板 ID
        gpuId: 0, // 默认 GPU ID
        memoryId: 0, // 默认内存 ID
        harddiskId: 0, // 默认硬盘 ID
        powersupplyId: 0, // 默认电源 ID
        coolingId: 0, // 默认散热 ID
        chassisId: 0, // 默认机箱 ID
        displayId: 0 // 默认显示器 ID
    };
}

// 定义过滤器的状态
export interface Filters {
    lowPrice: number, // 最低价格
    highPrice: number, // 最高价格
    sortBy: SortType, // 当前选择的排序方式
    cpuName: string[], // CPU 名称
    gpuName: string[], // GPU 名称
    motherboardName: string[], // 主板名称
    memoryName: string[], // 内存名称
};
// 初始化过滤器的状态
export const initFilters: Filters = {
    lowPrice: 0,
    highPrice: 99999,
    sortBy: SortType.NONE,
    cpuName: [],
    gpuName: [],
    motherboardName: [],
    memoryName: []
};

//获取所有解决方案
export const getAllSolutionNofilter = () => {
    return axios.get(`${SOLUTION_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}


// 获取所有解决方案（可以添加筛选参数）
export const getAllSolution = (filters: Filters) => {
    return axios.post(`${SOLUTION_MODULE}/allByFilter`, filters)
        .then(res => {
            return res.data.result;
        })
};


//自定义装机：保存一个装机方案
export const uploadSolution = (solution: SolutionVO) => {
    console.log(solution)
    return axios.post(`${SOLUTION_MODULE}/save`, solution)
        .then(res => {
            return res
        })
}


// 删除一个装机方案
export const deleteSolution = (id: number) => {
    return axios.post(`${SOLUTION_MODULE}/delete/${id}`)
        .then(res => {
            return res.data.result;
        })
}

// 收藏一个装机方案
export const starSolution = (id: number) => {
    return axios.post(`${SOLUTION_MODULE}/star/${id}`)
        .then(res => {
            return res.data.result;
        })
}

// 获取一个装机方案
export const getSolution = (id: number) => {
    return axios.get(`${SOLUTION_MODULE}/${id}`)
        .then(res => {
            return res.data.result;
        })
}

// 删除一个装机方案，返回 boolean
export const deleteSolutionById = (id: number) => {
    return axios.post(`${SOLUTION_MODULE}/delete/${id}`)
        .then(res => {
            return res.data.result;
        })
}


// 创建 OpenAI 客户端实例，直接传入 API 密钥
const openai = new OpenAI({
    baseURL: 'https://api.deepseek.com',
    apiKey: 'sk-3e217eaec54443fe803683ba6aea1d1d',
    dangerouslyAllowBrowser: true
});



const systemPrompt: string = `
The user may provide their preferred budget range for building a PC.
The user may specify the intended use of the PC, such as gaming, video editing, or general office work.
The user may indicate preferred brands for certain components (e.g., Intel, AMD, NVIDIA).
Please analyze the user's needs and extract the following information:
- "lowPrice" and "highPrice" can be chosen between 3732 and 40421.
- "cpuName" should be selected from the following list:
  ['i9 14900K', 'i7 14700KF', 'i7 14700K', 'i5 14600K', 'i9 13900K', 'i5 13600KF', 'i5 13600K', 'i5 13490F', 'i5 13400F', 'i5 13400', 'i5 12600KF', 'i5 12400F', 'AMD Ryzen5 9600X', 'AMD Ryzen7 7800X3D', 'AMD Ryzen5 7500F', 'AMD Ryzen7 5700X3D', 'AMD Ryzen7 5700X', 'AMD Ryzen5 5600G', 'AMD Ryzen5 5600'].
- "gpuName" should be selected from the following list:
  ['RTX4090', 'RTX4070SUPER', 'RTX4070', 'RTX4060Ti', 'RTX4060', 'RTX3060', 'RX7800XT', 'RX6750GRE', 'RX6750', 'RX6500XT'].
- "motherboardName" should be selected from the following list:
  ['B760M-PLUS', 'B760M', 'B660M', 'B650M-B', 'B650M', 'B550M-P', 'H610M', 'H510M', 'A520M-A', 'Z790'].
- "memoryName" should be selected from the following list:
  ['DDR4', 'DDR5'].

Please output the information in JSON format, including the components based on the user's input.

EXAMPLE INPUT: 
"我想要玩 3A 游戏，预算 15000 以内."

EXAMPLE JSON OUTPUT:
{
    "lowPrice": 10000,
    "highPrice": 15000,
    "cpuName": ["i7 13600K", "AMD Ryzen7 5700X"], 
    "gpuName": ["RTX4060", "RX6750"],
    "motherboardName": ["B650M", "B550M-P"],
    "memoryName": ["DDR4","DDR5"]
}
`;


// 定义一个新的 API 函数，接收 content 参数并返回 filters
export const parseUserNeed = async (content: string): Promise<Filters | null> => {
    // 发送请求到 OpenAI API 以解析需求
    const completion = await openai.beta.chat.completions.parse({
        model: "deepseek-chat",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: content }, // 使用传入的 content 参数
        ],
        response_format: {
            'type': 'json_object'
        }
    });

    // 获取解析结果
    const parsedFilters = completion.choices[0].message.content;

    // 添加 null 检查以确保 parsedFilters 不为 null
    if (parsedFilters) {
        const filters: Filters = JSON.parse(parsedFilters);
        return filters
    } else {
        console.error("解析结果为 null");
        return null; // 返回 null
    }
};

