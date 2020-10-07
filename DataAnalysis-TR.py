import io
import matplotlib.pyplot as plt
import numpy as np
import xlrd
import xlsxwriter

workbook = xlrd.open_workbook("dataCollector.xlsx")
sheet = workbook.sheet_by_index(0)

lables = []
for i in range(sheet.nrows):
    lables.append(sheet.cell_value(i, 0))
test1 = []
for i in range(sheet.nrows):
    test1.append(sheet.cell_value(i, 2))
test2 = []
for i in range(sheet.nrows):
    if sheet.cell_value(i, 4) == "":
        test2.append(0)
    else:
        test2.append(sheet.cell_value(i, 4))

# NOTE: if you have more than two tests add them here

ind = np.arange(sheet.nrows)
barWidth = 0.25
fig = plt.figure()
ax = fig.add_subplot(111)

rects1 = ax.bar(ind, test1, barWidth, color='#2d7f5e')
rects2 = ax.bar(ind + barWidth, test2, barWidth, color='#557f2d')

# NOTE: if you have more than two tests add more rects here

ax.set_xlabel('Tester Results', fontweight='bold')
ax.set_ylabel('Grades')
plt.xticks([r + barWidth for r in range(len(test1))], lables)
ax.legend((rects1[0], rects2[0]), ('test 1', 'test 2'))

# NOTE: if you had more than two tests please change the above row to:
# ax.legend((rects1[0], ... , rects(n)[0]), ('test 1', ... , 'test n')


def add_bar_value(rects):
    for rect in rects:
        height = rect.get_height()
        ax.text(rect.get_x()+rect.get_width()/2., 1.0*height, '%d'%int(height), ha='center', va='bottom')


add_bar_value(rects1)
add_bar_value(rects2)

# NOTE: if you had more than two tests add them here

plt.show() # this is an option

# save the plot into excel file
outputWorkbook = xlsxwriter.Workbook("plots.xlsx")
outputSheet = outputWorkbook.add_worksheet("Test Results")
imgdata = io.BytesIO()
fig.savefig(imgdata, format='png')
outputSheet.insert_image(2, 2, '', {'image_data': imgdata})
outputWorkbook.close()