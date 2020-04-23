package com.ruds.data;

import android.Manifest;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class PdfViewer extends Fragment {

    PDFView pdfView;
    String url;
    ProgressBar pdfViewerProgressbar;

    public PdfViewer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener()).check();
        Dexter.withContext(getContext()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener()).check();
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pdf_viewer, container, false);
        pdfView = view.findViewById(R.id.pdfView);
        pdfViewerProgressbar = view.findViewById(R.id.pdfViewerProgressbar);
        pdfViewerProgressbar.setVisibility(View.VISIBLE);
        FileLoader.with(getContext()).load(url).fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC).asFile(new FileRequestListener<File>() {
            @Override
            public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                pdfViewerProgressbar.setVisibility(View.GONE);
                File pdfFile = response.getBody();
                pdfView.fromFile(pdfFile).password(null).defaultPage(0).enableSwipe(true).swipeHorizontal(false).enableDoubletap(true).enableAnnotationRendering(true).load();
            }

            @Override
            public void onError(FileLoadRequest request, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}