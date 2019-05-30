package com.jyt.video.common.util;

import android.widget.Toast;
import com.jyt.video.App;
import com.jyt.video.common.entity.BaseJson;
import com.jyt.video.service.ServiceCallback;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxHelper {

    public static  ObservableTransformer schedulersTransformer() {
        return upstream -> {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }



    public static class SimpleConsume implements Consumer {
        ServiceCallback serviceCallback;
        public SimpleConsume(ServiceCallback serviceCallback) {
            this.serviceCallback = serviceCallback;
        }

        @Override
        public void accept(Object o) throws Exception {
            if (o instanceof BaseJson) {
                serviceCallback.getResult().invoke(((BaseJson) o).getCode(),((BaseJson) o).getData());

                if (((BaseJson) o).getCode()!=BaseJson.CODE_SUCCESS) {
                    ToastUtil.showShort(App.app, ((BaseJson) o).getMsg());
                }

            }else {
                serviceCallback.getResult().invoke(BaseJson.CODE_ERROR,null);
            }
        }
    }

    public static class ErrorConsume implements Consumer<Throwable>{
        ServiceCallback serviceCallback;
        public ErrorConsume(ServiceCallback serviceCallback) {
            this.serviceCallback = serviceCallback;
        }

        @Override
        public void accept(Throwable throwable) throws Exception {
            serviceCallback.getResult().invoke(BaseJson.CODE_ERROR,null);
            ToastUtil.showShort(App.app,throwable.getMessage());

        }
    }

    public static void simpleResult(Observable observable,ServiceCallback callback){
        observable.compose(schedulersTransformer()).subscribe(new SimpleConsume(callback),new ErrorConsume(callback));
    }
}
