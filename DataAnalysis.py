
import matplotlib.pyplot as plt
import numpy as np
import xlrd

workbook = xlrd.open_workbook("filepath")
sheet = workbook.sheet_by_index(0)

lables = []
for i in range(sheet.nrows):
    lables.append(sheet.cell_value(i, 0))
test1 = []
for i in range(sheet.nrows):
    test1.append(sheet.cell_value(i, 1))
test2 = []
for i in range(sheet.nrows):
    if sheet.cell_value(i, 2) == "":
        test2.append(0)
    else:
        test2.append(sheet.cell_value(i, 2))

# set width of bar
barWidth = 0.25

# Set position of bar on X axis
r1 = np.arange(len(test1))
r2 = [x + barWidth for x in r1]

# Make the plot
plt.bar(r1, test1, color='#2d7f5e', width=barWidth, edgecolor='white', label='test1')
plt.bar(r2, test2, color='#557f2d', width=barWidth, edgecolor='white', label='test2')

# Add xticks on the middle of the group bars
plt.xlabel('Tester Results', fontweight='bold')
plt.ylabel('Grade')
plt.xticks([r + barWidth for r in range(len(test1))], lables)

# Create legend & Show graphic
plt.legend()
plt.show()


