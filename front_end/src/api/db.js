import { axios } from '@/utils/request'
const api = {
    dbPre: '/api/db'
}
export function updateDatabaseAPI() {
    return axios({
        url: `${api.dbPre}/updateDatabase`,
        method: 'POST'
    })
}