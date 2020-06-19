<template>
  <div class="hotelList">
    <a-layout>
        <a-layout-content style="min-width: 800px">
            <a-row style="margin-bottom: 50px">
                <a-col :span="8" :offset="3">
                    排序方式：
                    <a-radio-group v-model="value" @change="sortChange">
                        <a-radio-button value="rate">
                            评分从高到低
                        </a-radio-button>
                        <a-radio-button value="star">
                            星级从高到低
                        </a-radio-button>
                        <a-radio-button value="price">
                            价格从低到高
                        </a-radio-button>
                    </a-radio-group>
                </a-col>
                <a-col :span="6" :offset="4">
                    <a-button @click="showSearchModal" type="primary" style="width: 100%">酒店搜索</a-button>
                </a-col>
            </a-row>
            <a-spin :spinning="hotelListLoading">
            <div class="card-wrapper">
                <HotelCard :hotel="item" v-for="item in hotelList" :key="item.index" @click.native="jumpToDetails(item.id)"></HotelCard>
                <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                </div>
                <a-pagination showQuickJumper :total="hotelList.totalElements" :defaultCurrent="1" @change="pageChange"></a-pagination>
            </div>
          </a-spin>
      </a-layout-content>
    </a-layout>
      <SearchModal></SearchModal>
  </div>
</template>
<script>
import HotelCard from './components/hotelCard'
import SearchModal from "./components/searchModal";
import { mapGetters, mapActions, mapMutations } from 'vuex'

export default {
  name: 'home',
  components: {
    HotelCard,
      SearchModal
  },
  data(){
    return{
      emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
        value: ''
    }
  },
  computed: {
    ...mapGetters([
      'hotelList',
      'hotelListLoading'
    ])
  },
    async mounted() {
        await this.getHotelList()
    },
  methods: {
    ...mapMutations([
      'set_hotelListParams',
      'set_hotelListLoading',
        'set_hotelListSortedByRate',
        'set_hotelListSortedByPrice',
        'set_hotelListSortedByStar',
        'set_searchModalVisible'
    ]),
      ...mapActions([
         'getHotelList'
      ]),
    sortChange(){
        if (this.value=="rate"){
            this.set_hotelListSortedByRate()
        } else if (this.value=="price"){
            this.set_hotelListSortedByPrice()
        } else if (this.value=="star"){
            this.set_hotelListSortedByStar()
        } else {

        }
    },
      showSearchModal() {
        this.set_searchModalVisible(true);
      },
    pageChange(page, pageSize) {
      const data = {
        pageNo: page - 1
      }
      this.set_hotelListParams(data)
      this.set_hotelListLoading(true)
        // this.getHotelList()
    },
    jumpToDetails(id){
      this.$router.push({ name: 'hotelDetail', params: { hotelId: id }})
    }
  }
}
</script>
<style scoped lang="less">
  .hotelList {
    text-align: center;
    padding: 50px 0;
    .emptyBox {
      height: 0;
      margin: 10px 10px
    }
    .card-wrapper{
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;
      flex-grow: 3;
      min-height: 800px
    }
    .card-wrapper .card-item {
      margin: 30px;
      position: relative;
      height: 188px;
    }
  }
</style>