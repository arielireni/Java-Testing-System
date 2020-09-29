import matplotlib.pyplot as plt
import numpy as np
import io
import xlrd
import xlsxwriter

workbook = xlrd.open_workbook("C:\\Users\\Eli\\Desktop\\testGrades\\dataCollector.xlsx")
sheet = workbook.sheet_by_index(1)

title_text = 'Common Mistakes'
fig_background_color = 'firebrick'
fig_border = 'lightcoral'
data = [['Question Number', 'Test Number', 'Expected Value', 'Average Grade']]
for i in range(sheet.nrows):
    data.append([str(sheet.cell_value(i, j)) for j in range(0, 4)])
column_headers = data.pop(0)
ccolors = plt.cm.Reds(np.full(len(column_headers), 0.1))

plt.figure(linewidth=2, edgecolor=fig_border, facecolor=fig_background_color, tight_layout={'pad':1},)
the_table = plt.table(cellText=data, rowLoc='right', colColours=ccolors, colLabels=column_headers, loc='center')
the_table.scale(1.5, 1.5)
the_table.auto_set_font_size(False)
the_table.set_fontsize(10)

ax = plt.gca()
ax.get_xaxis().set_visible(False)
ax.get_yaxis().set_visible(False)
plt.box(on=None)
plt.suptitle(title_text, size=20, color='w')
plt.draw()
fig = plt.gcf()
plt.show()

# save the plot into excel file
outputWorkbook = xlsxwriter.Workbook("C:\\Users\\Eli\\Desktop\\testGrades\\plots.xlsx")
outputSheet = outputWorkbook.add_worksheet("Common Mistakes")
imgdata = io.BytesIO()
fig.savefig(imgdata, format='png')
outputSheet.insert_image(2, 2, '', {'image_data': imgdata})
outputWorkbook.close()



