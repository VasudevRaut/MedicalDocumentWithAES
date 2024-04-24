package com.example.dfcsproject;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InputStreamRequest extends Request<InputStream> {

    private final Response.Listener<InputStream> mListener;

    public InputStreamRequest(int method, String url, Response.Listener<InputStream> listener,
                              Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<InputStream> parseNetworkResponse(NetworkResponse response) {
        try {
            byte[] responseData = response.data;
            ByteArrayInputStream inputStream = new ByteArrayInputStream(responseData);
            return Response.success(inputStream, null);
        } catch (Exception e) {
            return Response.error(new VolleyError(e));
        }
    }

    @Override
    protected void deliverResponse(InputStream response) {
        mListener.onResponse(response);
    }
}
