
from flask import Flask, render_template, request, url_for, Response, redirect
import tensorflow as tf
import json
import numpy as np
import io
import os
import requests
import PIL
from matplotlib import colors
import base64
from tensorflow import keras
app = Flask(__name__)

def normalize_input_img(img):
    img =tf.keras.preprocessing.image.img_to_array(img,dtype=np.int32)
    img = img/127.5
    img -= 1
    return img
def create_mask(img,cats):



    img =tf.keras.preprocessing.image.img_to_array(img,dtype=np.int32) # convert img to np.array
    img=np.squeeze(img) #remove 1 dimension
    mask = np.zeros((img.shape[0], img.shape[1], len(cats)),dtype=int) # create a mask with zeros
    flat_cat = [val for cat in list(cats.values()) for val in cat] # create a list of all values associated with categories
    ca_min=min(flat_cat)
    ca_max=max(flat_cat)
    cats_names=list(cats.keys())


    for i in range(ca_min,ca_max):
            for idx,name in enumerate(cats_names):
                if i in cats[name]:
                    mask[:,:,idx] = np.logical_or(mask[:,:,idx],(img==i))
    #print('mask',mask)
    return mask

def generate_img_from_mask(mask, cats, colors_palette=['b', 'g', 'r', 'c', 'm', 'y', 'k', 'w']):

    '''Generate a PIL image from a segmented mask and categorie cats with specified color palette for each cateorie

    Args:
      mask - numpy array of dimension (shape(img),len(cats))
      cats - dict {'cat1':[value1,value2,value3],'cat2':[value1,value2,value3]}

    Returns
      PIL image'''
    img_seg = np.zeros((mask.shape[0], mask.shape[1], 3), dtype='float')
    for cat in range(len(cats)):
        img_seg[:, :, 0] += mask[:, :, cat] * colors.to_rgb(colors_palette[cat])[0]
        img_seg[:, :, 1] += mask[:, :, cat] * colors.to_rgb(colors_palette[cat])[1]
        img_seg[:, :, 2] += mask[:, :, cat] * colors.to_rgb(colors_palette[cat])[2]

    return tf.keras.preprocessing.image.array_to_img(img_seg)

@app.route('/index/', methods=['GET','POST'])
def index():
    current_dir=os.getcwd()
    print(current_dir)
    file_list = os.listdir(current_dir+"\\static\\img_128_256\\train")
    print("ccc ",request.method)
    if request.method == 'get':
        file = request.form['files']
        filey = request.form['file_select']
        print('fichier select :', file)
        print('fichier selecty :', filey)
        return redirect(url_for('predict', file=file))
    if request.method == 'post':
        file = request.form['file']
        filey = request.form['file_select']
        print('fichier select :',file)
        print('fichier selecty :',filey)
        return redirect(url_for('predict',file=file))
    else:

        return render_template('index.html',file_list=file_list)


@app.route('/predict' , methods=['GET', 'POST'])
def predict():
    choosen_file = request.args.get('file')
    print(choosen_file)
    print('type :',type(choosen_file))
    current_dir = os.getcwd()
    test_img_file = current_dir + "\\static\\img_128_256\\train\\" + choosen_file
    test_mask_file = current_dir + "\\static\\img_128_256\\masks\\" + choosen_file.split('left')[0] + "gtFine_labelIds.png"
    model = keras.models.load_model('my_model', compile=False)

    model.compile(loss='categorical_crossentropy',
                  metrics=['accuracy']

                  )
    img = tf.keras.preprocessing.image.load_img(test_img_file, target_size=(128, 256))
    img_norm = np.array(normalize_input_img(img))
    mask = tf.keras.preprocessing.image.load_img(test_mask_file, target_size=(128, 256), color_mode="grayscale")
    cats = {'void': [0, 1, 2, 3, 4, 5, 6],
            'flat': [7, 8, 9, 10],
            'construction': [11, 12, 13, 14, 15, 16],
            'object': [17, 18, 19, 20],
            'nature': [21, 22],
            'sky': [23],
            'human': [24, 25],
            'vehicle': [26, 27, 28, 29, 30, 31, 32, 33, -1]}

    predicted_image = generate_img_from_mask(model.predict(img_norm.reshape(1, 128, 256, 3))[0], cats)

    mask_img = tf.keras.preprocessing.image.load_img(test_mask_file, target_size=(128, 256), color_mode="grayscale")
    original_mask = generate_img_from_mask(create_mask(mask_img, cats), cats)

    buffer = io.BytesIO()
    predicted_image.save(buffer, format="JPEG")

    buffer2 = io.BytesIO()
    original_mask.save(buffer2, format="JPEG")




    encoded_img_data = base64.b64encode(buffer.getvalue())
    encode_mask_data = base64.b64encode(buffer2.getvalue())

    return render_template('predict.html', file=choosen_file, img_data=encoded_img_data.decode('utf-8'),
                           mask_data=encode_mask_data.decode('utf-8'))

if __name__ == '__main__':
    app.run()
